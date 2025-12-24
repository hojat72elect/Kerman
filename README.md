# Kerman Game Engine

**Kerman** is a modular game engine currently in development, designed with a focus on clean architecture and strict separation of concerns.

## 🏗 Architecture

We follow a **top-down layered architecture**. From top to bottom it goes like this :

1. **Game Code** (User Project)
2. **Engine API**
3. **Systems** (Render, Physics, Input, Audio)
4. **Platform / Libraries**

---

## 🚀 Roadmap

### Core
- [ ] Game loop (update → render → sleep)
- [ ] Time management (delta time, FPS cap)
- [ ] Engine initialization / shutdown
- [ ] Global configuration
- [ ] Math utilities
- [ ] Logging
- [ ] Debug tools (FPS counter, bounding boxes)
- [ ] Serialization (save/load)

### GUI / Windowing
- [ ] Window creation
- [ ] Resolution & fullscreen handling
- [ ] Input focus, resize events

### Renderer
- [ ] Renderer abstraction (avoiding hardcoded OpenGL calls)
- [ ] Camera system
- [ ] Sprite batching
- [ ] Layers / draw order
- [ ] Shaders (planned)

### Input System
- [ ] Keyboard
- [ ] Mouse
- [ ] Gamepad (planned)
- [ ] Action mapping (e.g., “Jump” → Space / Button A)

### Scene Management
- [ ] Scene loading/unloading
- [ ] Scene transitions
- [ ] Entity registration

### Entity System
- [ ] Entity ID management
- [ ] Entity lifecycle (create, destroy, enable/disable)

### Physics Engine
- [ ] AABB collisions
- [ ] Spatial partitioning (grid, quadtree)
- [ ] Basic response (stop, slide)
- [ ] Rigid bodies
- [ ] Forces

### Audio System
- [ ] Sound effects
- [ ] Music streaming
- [ ] Volume groups
- [ ] Audio channels

### Asset Management
- [ ] Load textures, sounds, fonts
- [ ] Asset caching
- [ ] Reference counting
- [ ] Hot reloading

---