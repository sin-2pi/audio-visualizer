# UML Class Tables

## Package `com.cosc3011`

### windowMain

| Attributes             | Methods                               |
|------------------------|---------------------------------------|
| `- JFrame frame`       | `+ windowMain()`                      |
| `- JLabel errorMessageLabel` | `+ initialize()`                      |
| `- Color white`        | `- openFolderChooser()`               |
| `- Color black`        | `- showErrorWindow(String message)`   |
| `- Color dark_gray`    | `+ main(String[] args)`               |
| `- Color red`          |                                       |
| `- FileManager fm`     |                                       |

### programwindow *(inner class of windowMain)*

| Attributes                  | Methods                                                   |
|-----------------------------|-----------------------------------------------------------|
| `- static JFrame programFrame` | `+ programwindow(String projectName)`                    |
|                             | `+ static startVisualization(String filePath, String type)` |
|                             | `+ static getFrame()`                                     |

### vizMain

| Attributes | Methods                    |
|------------|---------------------------|
| *(none)*   | `+ main(String[] args)`   |

### FileManager

| Attributes                 | Methods                                                      |
|----------------------------|--------------------------------------------------------------|
| `- String baseDirectory`   | `+ FileManager(String baseDirectory)`                         |
| `- String projectDir`      | `+ boolean createProjectDirectory(String projectName)`        |
|                            | `+ String getProjectDir()`                                   |
|                            | `+ static boolean createSaveFolder(String path)`             |

### topMenu

| Attributes              | Methods                        |
|-------------------------|--------------------------------|
| `+ JMenuBar menuBar`    | `+ topMenu(FileManager fm)`    |

### FileMenu

| Attributes            | Methods                                               |
|-----------------------|-------------------------------------------------------|
| `- JFrame frame`      | `+ FileMenu()`                                        |
| `- String audioFile`  | `- static String getFileExtension(String fileName)`   |
|                       | `- static boolean isValidFileType(String fileExtension)` |
|                       | `+ static File openFileChooser(JFrame frame)`         |
|                       | `+ String getAudioFile()`                             |

### audioMenu

| Attributes               | Methods                                   |
|--------------------------|-------------------------------------------|
| `- FileManager myFM`     | `+ audioMenu()`                           |
| `- FileMenu fileMenu`    | `+ void setFileManager(FileManager fm)`   |
|                          | `+ void setFileMenu(FileMenu fm)`         |

### SettingsMenu

| Attributes | Methods              |
|------------|---------------------|
| *(none)*   | `+ SettingsMenu()`  |

### recordingMenu

| Attributes                   | Methods                                 |
|------------------------------|-----------------------------------------|
| `- GifCapture recording`     | `+ recordingMenu()`                     |
| `- FileManager myFM`         | `- void recordingSettingsMenu()`        |
| `- String savePath`          | `+ void setFileManager(FileManager fm)` |

### GifCapture

| Attributes                              | Methods                                |
|-----------------------------------------|----------------------------------------|
| `- ArrayList<BufferedImage> frames`     | `+ GifCapture()`                       |
| `- volatile boolean started`            | `+ void encodeGif()`                   |
| `- volatile boolean recording`          | `+ void startCapture(JFrame window)`   |
| `- AnimatedGifEncoder encoder`          | `+ boolean stopCapture()`              |
| `- String filePath`                     | `+ void captureFrame(JFrame window)`   |
| `- int delay`                           | `+ void updatePath(String newPath)`    |
| `+ int frameRate`                       | `+ void updateFPS(int fps)`            |
| `- static final JPanel square`          | `+ int getFPS()`                       |
| `- static int x`                        | `+ static void createAndShowGUI()`     |
|                                         | `+ static void main(String[] args)`    |

---

## Package `com.audioseperator`

### audioSeperator

| Attributes | Methods                                                         |
|------------|-----------------------------------------------------------------|
| *(none)*   | `+ static void run(String audioFile, String outputPath)`         |

### audioPlayer

| Attributes                        | Methods                              |
|-----------------------------------|--------------------------------------|
| `- Clip audioClip`                | `+ audioPlayer(String filePath, String windowName)` |
| `- WaveformPanel waveformPanel`   |                                      |
| `- File audioFile`                |                                      |
| `- int currentFrame`              |                                      |

### WaveformPanel

| Attributes         | Methods                            |
|--------------------|------------------------------------|
| `- byte[] audioData` | `+ WaveformPanel(File audioFile)`  |
|                    | `# void paintComponent(Graphics g)` |

### SeparationStrategy *(interface)*

| Methods                            |
|------------------------------------|
| `+ void process(float[] buffer)`    |
| `+ float[] getProcessedBuffer()`    |

### StrategySelector

| Attributes | Methods                                             |
|------------|-----------------------------------------------------|
| *(none)*   | `+ static SeparationStrategy getStrategy(String strategyName)` |

### StemProcessor

| Attributes                                  | Methods                                           |
|---------------------------------------------|---------------------------------------------------|
| `- static final int BUFFER_SIZE`            | `+ StemProcessor(SeparationStrategy strategy)`    |
| `- static final int OVERLAP`                | `+ void process(File inputFile, String outputPath)`|
| `- static final int SAMPLE_RATE`            |                                                   |
| `- final SeparationStrategy strategy`       |                                                   |

### BrassSeparationStrategy

| Attributes                                  | Methods                          |
|---------------------------------------------|----------------------------------|
| `- static final int BUFFER_SIZE`            | `+ BrassSeparationStrategy()`    |
| `- static final int OVERLAP`                | `+ void process(float[] frame)`  |
| `- static final int SAMPLE_RATE`            | `+ float[] getProcessedBuffer()` |
| `- final FloatFFT_1D fft`                   |                                  |
| `- final float[] window`                    |                                  |
| `- final float[] outputBuffer`              |                                  |
| `- int bufferOffset`                        |                                  |
| `- final float LOW_CUTOFF`                  |                                  |
| `- final float HIGH_CUTOFF`                 |                                  |

### SpectralSubtractionStrategy

| Attributes                                  | Methods                                               |
|---------------------------------------------|-------------------------------------------------------|
| `- static final int BUFFER_SIZE`            | `+ SpectralSubtractionStrategy()`                     |
| `- static final int OVERLAP`                | `+ void process(float[] frame)`                       |
| `- static final int SAMPLE_RATE`            | `+ float[] getProcessedBuffer()`                      |
| `- final FloatFFT_1D fft`                   | `- float[] estimateMagnitude(float[] complex)`        |
| `- final float[] window`                    | `- float[] estimatePhase(float[] complex)`            |
| `- final float[] outputBuffer`              |                                                       |
| `- float[] noiseEstimate`                   |                                                       |
| `- int bufferOffset`                        |                                                       |

### AudioWriter

| Attributes | Methods                                                                       |
|------------|-------------------------------------------------------------------------------|
| *(none)*   | `+ static void writeWav(float[] samples, File outputFile, int sampleRate)`     |
