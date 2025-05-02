# Audio Visualizer Use Case Tables

## Program Control Use Cases

### Open Program
| Aspect | Description |
|--------|-------------|
| Related Requirements | Open program |
| Goal In Context | Start the audio visualizer application |
| Preconditions | Application is installed on the system |
| Successful End Condition | Application launches successfully and displays the main interface |
| Failed End Condition | Application fails to launch |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User executes the application |
| Main Flow | 1. User clicks on the application executable<br>2. System initializes components<br>3. Main window appears with options to create or open a project |
| Extensions | Application crashes during initialization |

### Exit Program
| Aspect | Description |
|--------|-------------|
| Related Requirements | Exit program |
| Goal In Context | Close the audio visualizer application |
| Preconditions | Application is running |
| Successful End Condition | Application closes gracefully |
| Failed End Condition | Application hangs or fails to close |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects exit option |
| Main Flow | 1. User selects File > Exit or clicks the close button<br>2. System prompts to save any unsaved changes<br>3. Application closes |
| Extensions | Application fails to close properly |

## File Management Use Cases

### Create Project
| Aspect | Description |
|--------|-------------|
| Related Requirements | Create a project |
| Goal In Context | Create a new audio visualization project |
| Preconditions | Application is running |
| Successful End Condition | New project is created and ready for audio file import |
| Failed End Condition | Project creation fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects "Create a new project" option |
| Main Flow | 1. User selects "Create a new project"<br>2. System prompts for project name<br>3. User enters project name<br>4. System creates project directory<br>5. System opens the project workspace |
| Extensions | Project creation fails due to invalid name or permissions |

### Open Project
| Aspect | Description |
|--------|-------------|
| Related Requirements | Open a project |
| Goal In Context | Open an existing audio visualization project |
| Preconditions | Application is running and project exists |
| Successful End Condition | Project is loaded and displayed in the workspace |
| Failed End Condition | Project fails to open |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects "Open an existing project" option |
| Main Flow | 1. User selects "Open an existing project"<br>2. System displays file browser<br>3. User selects project file<br>4. System loads project and displays in workspace |
| Extensions | Project file is corrupted or not found |

### Save Project
| Aspect | Description |
|--------|-------------|
| Related Requirements | Save project |
| Goal In Context | Save the current state of the project |
| Preconditions | Project is open and changes have been made |
| Successful End Condition | Project state is saved to disk |
| Failed End Condition | Project fails to save |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects save option |
| Main Flow | 1. User selects File > Save<br>2. System saves project state to disk |
| Extensions | Save operation fails due to disk space or permissions |

### Upload File
| Aspect | Description |
|--------|-------------|
| Related Requirements | Uploading a file |
| Goal In Context | Import an audio file into the project |
| Preconditions | Project is open |
| Successful End Condition | Audio file is imported and ready for processing |
| Failed End Condition | File import fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects file import option |
| Main Flow | 1. User selects option to import audio file<br>2. System displays file browser<br>3. User selects audio file (.wav or .mp3)<br>4. System validates and imports the file |
| Extensions | File format is invalid or file is corrupted |

## Visualization Use Cases

### Import Layer
| Aspect | Description |
|--------|-------------|
| Related Requirements | Import layer |
| Goal In Context | Import a specific audio layer (drums, vocals, etc.) |
| Preconditions | Project is open with audio file imported |
| Successful End Condition | Layer is imported and ready for visualization |
| Failed End Condition | Layer import fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects layer import option |
| Main Flow | 1. User selects option to import layer<br>2. System displays available layers<br>3. User selects specific layer<br>4. System imports the layer |
| Extensions | Layer cannot be extracted or identified |

### Select Layer to Process
| Aspect | Description |
|--------|-------------|
| Related Requirements | Select layer to process |
| Goal In Context | Select specific layers for visualization |
| Preconditions | Layers have been imported |
| Successful End Condition | Selected layers are marked for visualization |
| Failed End Condition | Layer selection fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects layers from available options |
| Main Flow | 1. System displays available layers<br>2. User selects desired layers (bass, keys, percussion, etc.)<br>3. System marks selected layers for visualization |
| Extensions | Selected layers cannot be processed |

### Export Visualization
| Aspect | Description |
|--------|-------------|
| Related Requirements | Export layer(s) of visualization |
| Goal In Context | Export the visualization as a file |
| Preconditions | Visualization has been created |
| Successful End Condition | Visualization is exported to a file |
| Failed End Condition | Export operation fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects export option |
| Main Flow | 1. User selects option to export visualization<br>2. System prompts for export format and location<br>3. User provides export details<br>4. System exports visualization to specified location |
| Extensions | Export fails due to disk space or permissions |

## Recording Use Cases

### Start Recording
| Aspect | Description |
|--------|-------------|
| Related Requirements | Recording functionality |
| Goal In Context | Begin recording the visualization |
| Preconditions | Visualization is active |
| Successful End Condition | Recording begins capturing frames |
| Failed End Condition | Recording fails to start |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects start recording option |
| Main Flow | 1. User selects Recording > Start<br>2. System begins capturing frames of the visualization |
| Extensions | Recording fails to initialize |

### Stop Recording
| Aspect | Description |
|--------|-------------|
| Related Requirements | Recording functionality |
| Goal In Context | Stop the active recording |
| Preconditions | Recording is in progress |
| Successful End Condition | Recording stops and frames are ready for saving |
| Failed End Condition | Recording fails to stop properly |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects stop recording option |
| Main Flow | 1. User selects Recording > Stop<br>2. System stops capturing frames<br>3. System prepares captured frames for saving |
| Extensions | Recording process cannot be terminated properly |

### Save Recording
| Aspect | Description |
|--------|-------------|
| Related Requirements | Recording functionality |
| Goal In Context | Save the recorded visualization as a GIF |
| Preconditions | Recording has been stopped |
| Successful End Condition | Recording is saved as a GIF file |
| Failed End Condition | Save operation fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects save recording option |
| Main Flow | 1. User selects Recording > Save Recording<br>2. System displays save dialog<br>3. User specifies save location<br>4. System encodes and saves the GIF |
| Extensions | Encoding fails or disk space is insufficient |

### Configure Recording Settings
| Aspect | Description |
|--------|-------------|
| Related Requirements | Recording functionality |
| Goal In Context | Configure recording parameters (FPS, save path) |
| Preconditions | Application is running |
| Successful End Condition | Recording settings are updated |
| Failed End Condition | Settings update fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects recording settings option |
| Main Flow | 1. User selects Recording > Settings<br>2. System displays settings dialog<br>3. User modifies FPS and save path<br>4. System validates and saves settings |
| Extensions | Settings validation fails |

## Settings Use Cases

### Configure Preferences
| Aspect | Description |
|--------|-------------|
| Related Requirements | Application preferences |
| Goal In Context | Configure application preferences |
| Preconditions | Application is running |
| Successful End Condition | Preferences are updated |
| Failed End Condition | Preferences update fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects preferences option |
| Main Flow | 1. User selects Settings > Preferences<br>2. System displays preferences dialog<br>3. User modifies preferences<br>4. System saves updated preferences |
| Extensions | Preferences cannot be saved |

### Select Theme
| Aspect | Description |
|--------|-------------|
| Related Requirements | Application appearance |
| Goal In Context | Change the application's visual theme |
| Preconditions | Application is running |
| Successful End Condition | Application theme is updated |
| Failed End Condition | Theme change fails |
| Primary Actors | User |
| Secondary Actors | None |
| Trigger | User selects theme option |
| Main Flow | 1. User selects Settings > Themes<br>2. System displays available themes<br>3. User selects desired theme<br>4. System applies the selected theme |
| Extensions | Theme cannot be applied |
