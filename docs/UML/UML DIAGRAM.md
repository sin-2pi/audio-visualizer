```mermaid
classDiagram
%% Package com.cosc3011
class windowMain {
-JFrame frame
-JLabel errorMessageLabel
-Color white
-Color black
-Color dark_gray
-Color red
-FileManager fm
+windowMain()
+initialize()
-openFolderChooser()
-showErrorWindow(String message)
+main(String[] args)
}
class programwindow {
    -static JFrame programFrame
    +programwindow(String projectName)
    +static startVisualization(String filePath, String type)
    +static getFrame()
}

class vizMain {
    +main(String[] args)
}

class FileManager {
    -String baseDirectory
    -String projectDir
    +FileManager(String baseDirectory)
    +boolean createProjectDirectory(String projectName)
    +String getProjectDir()
    +static boolean createSaveFolder(String path)
}

class topMenu {
    +JMenuBar menuBar
    +topMenu(FileManager fm)
}

class FileMenu {
    -JFrame frame
    -String audioFile
    +FileMenu()
    -static String getFileExtension(String fileName)
    -static boolean isValidFileType(String fileExtension)
    +static File openFileChooser(JFrame frame)
    +String getAudioFile()
}

class audioMenu {
    -FileManager myFM
    -FileMenu fileMenu
    +audioMenu()
    +void setFileManager(FileManager fm)
    +void setFileMenu(FileMenu fm)
}

class SettingsMenu {
    +SettingsMenu()
}

class recordingMenu {
    -GifCapture recording
    -FileManager myFM
    -String savePath
    +recordingMenu()
    -void recordingSettingsMenu()
    +void setFileManager(FileManager fm)
}

class GifCapture {
    -ArrayList~BufferedImage~ frames
    -volatile boolean started
    -volatile boolean recording
    -AnimatedGifEncoder encoder
    -String filePath
    -int delay
    +int frameRate
    -static final JPanel square
    -static int x
    +GifCapture()
    +void encodeGif()
    +void startCapture(JFrame window)
    +boolean stopCapture()
    +void captureFrame(JFrame window)
    +void updatePath(String newPath)
    +void updateFPS(int fps)
    +int getFPS()
    +static void createAndShowGUI()
    +static void main(String[] args)
}

%% Package com.audioseperator
class audioSeperator {
    +static void run(String audioFile, String outputPath)
}

class audioPlayer {
    -Clip audioClip
    -WaveformPanel waveformPanel
    -File audioFile
    -int currentFrame
    +audioPlayer(String filePath, String windowName)
}

class WaveformPanel {
    -byte[] audioData
    +WaveformPanel(File audioFile)
    #void paintComponent(Graphics g)
}

class SeparationStrategy {
    <<interface>>
    +void process(float[] buffer)
    +float[] getProcessedBuffer()
}

class StrategySelector {
    +static SeparationStrategy getStrategy(String strategyName)
}

class StemProcessor {
    -static final int BUFFER_SIZE
    -static final int OVERLAP
    -static final int SAMPLE_RATE
    -final SeparationStrategy strategy
    +StemProcessor(SeparationStrategy strategy)
    +void process(File inputFile, String outputPath)
}

class BrassSeparationStrategy {
    -static final int BUFFER_SIZE
    -static final int OVERLAP
    -static final int SAMPLE_RATE
    -final FloatFFT_1D fft
    -final float[] window
    -final float[] outputBuffer
    -int bufferOffset
    -final float LOW_CUTOFF
    -final float HIGH_CUTOFF
    +BrassSeparationStrategy()
    +void process(float[] frame)
    +float[] getProcessedBuffer()
}

class SpectralSubtractionStrategy {
    -static final int BUFFER_SIZE
    -static final int OVERLAP
    -static final int SAMPLE_RATE
    -final FloatFFT_1D fft
    -final float[] window
    -final float[] outputBuffer
    -float[] noiseEstimate
    -int bufferOffset
    +SpectralSubtractionStrategy()
    +void process(float[] frame)
    +float[] getProcessedBuffer()
    -float[] estimateMagnitude(float[] complex)
    -float[] estimatePhase(float[] complex)
}

class AudioWriter {
    +static void writeWav(float[] samples, File outputFile, int sampleRate)
}

%% Relationships
windowMain *-- programwindow : inner class
windowMain -- FileManager : uses
vizMain -- windowMain : creates

topMenu -- FileMenu : contains
topMenu -- audioMenu : contains
topMenu -- SettingsMenu : contains
topMenu -- recordingMenu : contains
topMenu -- FileManager : uses

audioMenu -- FileManager : uses
audioMenu -- FileMenu : uses
audioMenu -- programwindow : uses

recordingMenu -- GifCapture : uses
recordingMenu -- FileManager : uses

audioSeperator -- StrategySelector : uses
audioSeperator -- StemProcessor : uses

audioPlayer *-- WaveformPanel : contains

StemProcessor -- SeparationStrategy : uses
StemProcessor -- AudioWriter : uses

StrategySelector ..> SeparationStrategy : creates
StrategySelector ..> BrassSeparationStrategy : creates
StrategySelector ..> SpectralSubtractionStrategy : creates

BrassSeparationStrategy ..|> SeparationStrategy : implements
SpectralSubtractionStrategy ..|> SeparationStrategy : implements

programwindow -- audioPlayer : creates
