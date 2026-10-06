# OpenEvon

Reconstructed, deobfuscated and de-branded source for the **Evon** Minecraft client
(original build `1.0 b123025`), rebuilt so that it compiles and runs as a normal
Fabric mod — **with the licence/HWID authentication layer removed**.

* **Minecraft:** 1.21.10
* **Loader:** Fabric Loader 0.17.3 / Fabric API 0.138.4+1.21.10
* **Mappings:** Yarn `1.21.10+build.3`
* **Java:** 21

---

## Quick start

Double-click **`run-client.bat`**, or from a terminal:

```bat
gradlew.bat runClient
```

To produce a distributable mod jar:

```bat
gradlew.bat build
:: -> build/libs/evon-1.0.0.jar
```

The first launch takes a while: Gradle has to set up the Loom dev environment and
download the Minecraft assets once.

---

## What was changed

### Authentication removed

The original client shipped an obfuscated native library
(`club/evon/launch/x64-windows.dll`) which owned every `native` method in the client
and performed the licence check. Its descriptor table showed it building a hardware
fingerprint (`DigestUtils.sha256Hex` over `System.getenv`, `System.getProperty` and
`RuntimeMXBean.getInputArguments`) and running a WebSocket handshake against the
author's server, gating startup on a semaphore that was only released after a
successful response.

Removed:

| Removed | What it was |
| --- | --- |
| `x64-windows.dll` | The enforcement point: HWID, licence check, and the native method bodies |
| `AuthWebSocket`, `IrcWebSocketClient`, `PinnedCertificateTrustManager` | The auth/IRC socket layer and its pinned-certificate trust manager |
| `Loader.attachNatives` | Now a no-op — nothing is `System.load`-ed any more |
| the auth bootstrap in `EvonClient` | No socket, no login gate |

There is **no** native library and **no** auth class in the built jar. The client no
longer contacts any licence server and starts without an account. Leftovers such as
`WebSocketMessage` / `IrcRecord*` are inert.

**AltManager is intact.** `AccountAuthService`, `MicrosoftLoginManager`,
`AccountConfig`, `AltManagerScreen` and the `LoginMethodButton`s only ever talked to
Microsoft/Mojang, never to the client's own server.

### Native methods reimplemented in Java

Every `native` declaration now has a real Java body:

* `EvonClient` — the bootstrap that the native layer used to perform: service
  singletons, FlatLaf, executor/semaphore, and the module + command registries.
* `KillAura.S_r4` / `KillAura.V` — targeting, rotation and per-tick state. The real
  constant table (`STRINGS_TABLE`, `SWITCH_COOLDOWN`, `encryptedConstants`) was
  recovered verbatim by running the original class against the DLL.
* `HypixelTargetFilter.d` / `q` — packet tracking for the NPC/entity filter.
* `ChatNameManager.i` / `y` — the IRC user-list feed, reduced to no-ops.

### Bugs fixed on the way

The rebuilt client had never actually run, so a number of latent defects only became
reachable once it did:

* **All 64 `IndyRuntime.invoke` call sites** passed stale bootstrap-method names (and
  four stale class names) inside string literals that an earlier rename pass never
  updated.
* **17 string-decryptor loops** indexed a 200-character blob with a `byte`, overflowing
  at 128.
* **`NvgFont.bootstrap`** was never decompiled — Procyon left a stub that threw, which
  broke every text draw.
* **`DoubleSetting.J()`** returned the `final` *minimum* rather than the mutable value
  (the original bytecode had two methods differing only by return type, which Java
  cannot express). Every setting silently read its minimum — that is what pushed the
  first-person hand off-screen and pinned every module range to its floor.
* **AWT is unusable inside Minecraft:** `net.minecraft.client.main.Main`'s static
  initialiser sets `java.awt.headless=true`, so the clipboard and the Swing dialogs in
  the AltManager always threw `HeadlessException` and crashed the game. Clipboard access
  now uses Minecraft's GLFW clipboard and login feedback goes to the chat HUD and the
  log instead of a `JOptionPane`.
* **Module lifecycle and target filtering:** Several decompiled `onDisable` methods
  called themselves recursively; they now delegate to the base lifecycle. Module
  keybinds no longer rely on a runtime-missing enum switch-map class, and Hypixel
  filtering handles the period before location metadata arrives.

---

## Commands

Chat commands are prefixed with `.` (`Settings -> prefix` is `.`).

| Command | Description |
| --- | --- |
| `.name <name>` | Sets the local user name shown on the watermark |
| `.clientname <name>` | Rebrands the client (watermark title, chat tag, main menu) |
| `.clientname -reset` | Restores the default client name |
| `.bind` / `.unbind` | Bind or unbind a module to a key |
| `.friend` | Manage the friend list |
| `.config` | Save / load / list configs |
| `.panic` | Disable every module and save the current config |
| `.help` | List all commands |
| `.ircchat`, `.online` | IRC chat toggle and online-user list |

`File > run-client.bat` launches the client; the ClickGui is bound to **Right Shift**
by default.

---

## Project layout

```
src/main/java/club/evon/
  EvonClient.java        client bootstrap and singletons
  ClientContext.java     MinecraftClient / run-dir / Gson access
  api/                   accounts, Microsoft login, Hypixel API, server detection
  command/               chat commands
  config/                module configs and the alt account store
  data/                  value types (settings data, user, notifications, ...)
  event/                 event bus and event types
  launch/                LaunchProperties, the (now inert) native loader shim
  manager/               module/command/config/font/rotation managers
  mixins/                ~90 Fabric mixins
  module/                BaseModule plus 50 modules and their sub-modes
  setting/               setting types (boolean, double, enum, bind, colour, ...)
  ui/                    ClickGui, AltManager, main menu, HUD components
  util/                  rendering, networking, math and text helpers
src/main/resources/      mixin config, access widener, fonts, images
libs/                    mod-api (build dependency) and the original client jar
```

---

## Notes and limitations

* The decompiled code still contains the obfuscator's flow-guard fields (methods named
  `E()`, `f()`, `getStateArray()`, `getFlowGuard()`, and so on). Their null/non-null
  state selects which copy of a duplicated branch is the live one; changing them
  changes behaviour.
* Some constants that lived only in the removed DLL are unrecoverable. Where that is
  the case the code falls back to a documented neutral value and says so in a `NOTE`
  comment — most visibly in `KillAura.V`, whose lag handling no longer blocks the
  client thread.
* `libs/evon-original.jar` is the untouched original build, kept for reference. It
  still contains the native protection library and is **not** used by the build.
