## Kerman Gae Engine

**Modules to be implemented:**

<ol>

<li>Core</li>
- [] Game loop (update → render → sleep)
- [] Time management (delta time, FPS cap)
- [] Engine initialization / shutdown
- [] Global configuration
- [] Math
- [] Logging
- [] Debug tools (FPS counter, bounding boxes)
- [] Serialization (save/load)

<li>GUI</li>
- [] Window creation
- [] Resolution & fullscreen handling
- [] Input focus, resize events

<li>Renderer</li>
- [] Renderer abstraction (don’t hardcode OpenGL calls everywhere)
- [] Camera system
- [] Sprite batching
- [] Layers / draw order
- [] Shaders (optional at first)

<li>Input System</li>
- [] Keyboard
- [] Mouse
- [] Gamepad (later)
- [] Action mapping (e.g. “Jump” → Space / Button A)

<li>Scene Management</li>
- [] Scene loading/unloading
- [] Scene transitions
- [] Entity registration

<li>Entity System</li>
- [] Entity ID management
- [] Entity lifecycle (create, destroy, enable/disable)

<li>Physics Engine</li>
- [] AABB collisions
- [] Spatial partitioning (grid, quadtree)
- [] Basic response (stop, slide)
- [] Rigid bodies
- [] Forces

<li>Audio System</li>
- [] Sound effects
- [] Music streaming
- [] Volume groups
- [] Audio channels

<li>Asset Management</li>
- [] Load textures, sounds, fonts
- [] Cache assets
- [] Reference counting
- [] Hot reloading

</ol>

**This engine is going to have a top-down layered architecture, something like this:**

Game Code (user project)
-----------------------
Engine API
-----------------------
Systems (Render, Physics, Input, Audio)
-----------------------
Platform / Libraries
-----------------------