# ⚡ Lightning Powers

**Harness the power of lightning and become supercharged!**

Lightning Powers is a Minecraft Fabric mod that introduces a craftable **Lightning Rod Helmet**. Instead of fearing thunderstorms, you can now use lightning strikes to unlock temporary superhuman abilities!

Get struck by lightning while wearing your special helmet to restore your health, refill your hunger, gain powerful status effects, and unleash electrical attacks.

Every major ability can be customized through the in-game **Mod Menu** configuration screen or a JSON configuration file.

![Lightning Powers Logo](YOUR_LOGO_IMAGE_URL)

---

## ⚡ Features

### 🪖 Lightning Rod Helmet

Craft and equip a special copper helmet featuring a lightning rod on top.

- Custom helmet item with a unique pixel-art texture
- Wearable 3D helmet model
- Copper design with glowing blue energy details
- Visible lightning rod attached to the helmet
- Normal and charged visual states
- Available through the Creative inventory
- Fully craftable in Survival mode

![Lightning Rod Helmet](YOUR_HELMET_IMAGE_URL)

### 🌩️ Lightning-Powered Abilities

When lightning strikes a player wearing the Lightning Rod Helmet, the helmet absorbs the electrical energy and activates **Supercharged Mode**.

Depending on your configuration, the player can receive:

- ❤️ **Full Health** – Instantly restore your health
- 🍗 **Full Hunger** – Refill hunger and saturation
- 💪 **Strength** – Deal increased melee damage
- 🏃 **Speed** – Move faster
- 🦘 **Jump Boost** – Jump higher
- 🛡️ **Resistance** – Reduce incoming damage
- 💚 **Regeneration** – Recover health over time
- 🔥 **Fire Resistance** – Resist fire damage
- 👁️ **Night Vision** – See clearly in darkness
- ⛏️ **Haste** – Mine blocks faster

The duration and strength of your abilities can be customized.

You can also add other registered Minecraft status effects, including compatible effects from other mods.

![Supercharged Player](YOUR_SUPERCHARGED_IMAGE_URL)

### ⚔️ Electric Combat

While supercharged, your attacks become electrically enhanced!

- Deal additional electrical melee damage
- Generate electric particles during combat
- Customize the additional damage
- Enable or disable electric combat independently

Turn your character into a lightning-powered warrior!

### ✨ Electric Visual Effects

Supercharged players can emit electrical particles, making their temporary powers visually recognizable.

The helmet also features glowing energy details that distinguish its charged appearance from its normal state.

![Electric Effects](YOUR_EFFECTS_IMAGE_URL)

### 🔥 Lightning Protection

Lightning is powerful, but your helmet is designed to help protect you from its dangerous side effects.

When the relevant settings are enabled:

- Prevent lightning damage while wearing the helmet
- Extinguish fire caused by a lightning strike
- Prevent the player from remaining on fire during Supercharged Mode

*Note: Lightning may still ignite nearby blocks and the environment.*

---

## 🔨 Crafting Recipe

Create your Lightning Rod Helmet using:

- **4× Copper Blocks**
- **1× Lightning Rod**
- **1× Diamond Helmet**

Arrange the materials in a crafting table:

| Copper Block | Lightning Rod | Copper Block |
|:---:|:---:|:---:|
| Copper Block | Diamond Helmet | Copper Block |
| Empty | Empty | Empty |

**Result: 1× Lightning Rod Helmet**

![Crafting Recipe](YOUR_CRAFTING_IMAGE_URL)

You can also obtain the helmet using:

`/give @s lightningpowers:lightning_helmet`

---

## ⚙️ Configuration

Lightning Powers is designed to be highly customizable.

You can adjust the mod directly through **Mod Menu** or by editing its JSON configuration file.

### Available Settings

| Setting | Description |
|---|---|
| Supercharge Duration | How long lightning-powered abilities remain active |
| Cooldown | Time required before the helmet can activate again |
| Restore Health | Instantly refill the player's health |
| Restore Hunger | Restore hunger and saturation |
| Status Effects | Add, remove, and configure potion effects |
| Electric Melee Damage | Customize additional melee damage |
| Electric Particles | Enable or disable electrical visual effects |
| Helmet Durability | Configure durability consumption |
| Lightning Damage Protection | Protect the wearer from lightning damage |
| Fire Protection | Prevent the player from burning during activation |

### JSON Configuration

The configuration file is located at:

`config/lightningpowers.json`

Status effects can be configured using Minecraft's registered effect identifiers.

For example:

```json
"effects": [
  "minecraft:strength:2",
  "minecraft:speed:1",
  "minecraft:regeneration:1",
  "minecraft:fire_resistance:0"
]
```

Effect amplifier values start at `0`, which corresponds to level I.

This system allows you to create your own combinations of abilities, from a small temporary boost to an extremely powerful lightning transformation.

![Mod Menu Configuration](YOUR_CONFIG_IMAGE_URL)

---

## 🌍 Languages

Lightning Powers currently includes:

- 🇬🇧 English
- 🇩🇪 German

The mod automatically uses the selected Minecraft language for its supported interface translations.

---

## 📦 Requirements

- **Minecraft:** Java Edition 26.3
- **Mod Loader:** Fabric
- **Fabric API:** Required
- **Mod Menu:** Supported for in-game configuration
- **Cloth Config:** Used for the configuration GUI

Make sure to use compatible dependency versions for your Minecraft installation.

---

## 🎮 How to Play

1. Craft the Lightning Rod Helmet.
2. Equip it in your helmet armor slot.
3. Wait for a thunderstorm or summon a lightning bolt using commands.
4. Get struck by lightning while wearing the helmet.
5. Become supercharged and enjoy your temporary abilities!

For testing, you can summon lightning using:

`/summon minecraft:lightning_bolt ~ ~ ~`

**Tip:** Experiment with different configuration settings to create your own lightning-powered playstyle.

---

## 🛠️ Customization & Compatibility

Lightning Powers was designed with flexibility in mind.

Most gameplay-related settings can be adjusted without modifying the source code.

The configurable status-effect system supports valid registered effects, making it possible to customize your powers beyond the default set.

Whether you prefer balanced survival gameplay or overpowered sandbox fun, Lightning Powers lets you decide how strong your character becomes.

---

## 📸 Screenshots

![Helmet in Creative Inventory](YOUR_CREATIVE_INVENTORY_IMAGE_URL)

![Helmet Equipped](YOUR_WORN_HELMET_IMAGE_URL)

![Lightning Strike](YOUR_LIGHTNING_STRIKE_IMAGE_URL)

![Supercharged Mode](YOUR_SUPERCHARGED_SCREENSHOT_URL)

---

## ⚡ Become the Storm!

**Don't run from lightning. Harness it!**

With Lightning Powers, thunderstorms become opportunities to transform your character into a supercharged force of nature.

Craft your helmet, attract lightning, and unleash your powers!