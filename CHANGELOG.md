## [2.0.0] - 2026-08-23
- Major rewrite to all APIs, mainly to support better long-term maintenance and have fundamentally
  better support for implementing new network features based on ModernNetworking's API.
  - The previous v1 API should still be supported, albeit marked as deprecated. No mods utilizing
    ModernNetworking v1's API should break.
- Implement login and configuration network phases
- Fix Bukkit plugins using ModernNetworking being unable to communicate with its clients
- Fix many KotlinLangForge issues
