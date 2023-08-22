# Scanner Code
> <b>Author: Nicola De Nicolais</b>

## 📄 Description
Android application built with Kotlin and Jetpack Compose that allows you to scan QR codes and barcodes. In the app it's possible to copy the content of the code or share it with other applications.<br/>

## 🔨  How to install and run the project
Clone this repository :<br/>
`
git clone https://github.com/ndenicolais/ScannerCode.git
`

Import the project into Android Studio :

1. File -> New -> Import Project
2. Browse to <path_to_project>
3. Click "OK"

Create a new virtual device or connect an Android device to your computer.</br>
Click Run to start the project on the selected device.

## 🛠️ Built with
Kotlin</br>
Jetpack Compose

## 📚 Package Structure

```
com.denicks21.scannercode       # ROOT PACKAGE
│
├── ui                          # UI FOLDER
│   ├── theme                   # THEME FOLDER
|   │   ├── Color               # Color palette used by the app.
|   │   ├── Shape               # Components shapes of Compose used by the app.
|   │   ├── Theme               # Theme used by the app.
|   │   ├── Type                # Typography styles for the fonts used by the app.
│   ├── components              # COMPONENTS FOLDER
|   │   ├── CameraDialog        # .
|   │   ├── ScanSheet           # .
|   │   ├── TopBar              # Bar that represent the app name and drawer menu.
|
├── MainActivity                # Main activity
```

## 📎 Screenshots
<p float="left">
<img height="500em" src="images/screen.png" title="ScannerCode's screen preview">
