# Marie's Compat

[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Minecraft](https://img.shields.io/badge/minecraft-1.21.1-brightgreen.svg)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/neoforge-21.1.229-orange.svg)](https://neoforged.net/)
[![Version](https://img.shields.io/badge/version-0.1.0--beta.1-blueviolet.svg)](https://github.com/kgbcupcake/MariesCompat/releases)
[![Build](https://img.shields.io/github/actions/workflow/status/kgbcupcake/MariesCompat/build.yml?branch=main)](https://github.com/kgbcupcake/MariesCompat/actions)

Optional companion mod for [MarieLib](https://github.com/kgbcupcake/MariesLib), providing integrations with:

- **Peak Stamina**
- **Spice of Life: Onion**
- **Legendary Survival Overhaul**

## Requirements

| Dependency   | Version       |
| ------------ | ------------- |
| Minecraft    | 1.21.1        |
| NeoForge     | 21.1.229+     |
| MarieLib     | 0.1.1-beta.4+ |
| Cloth Config | 15.0.140      |

## Installation

1. Install [NeoForge](https://neoforged.net/) for Minecraft 1.21.1.
2. Install [MarieLib](https://github.com/kgbcupcake/MariesLib) (required dependency).
3. Drop the `MariesCompat` jar into your `mods` folder.
4. Install any of the supported compat mods (Peak Stamina, Spice of Life: Onion, Legendary Survival Overhaul) you want integrations for — they are all optional.

## Building from source

```bash
./gradlew build
```

The built jar will be in `build/libs/`.

## License

Distributed under the [MIT License](LICENSE).
