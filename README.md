# Nura Auto Accept (Android / Kotlin)

A minimal Android project intended to detect an accessibility-exposed control whose text is exactly `Accept`, then click it when the user enables auto-clicking.

## Build APK on GitHub
1. Create a GitHub repository and upload all files in this project.
2. Push to the `main` branch, or open **Actions → Build Nura Auto Accept APK → Run workflow**.
3. Download the `Nura-Auto-Accept-debug` artifact from the completed workflow.

## On the phone
1. Install the generated debug APK.
2. Open Nura Auto Accept.
3. Tap **Open Accessibility Settings** and enable **Nura Accept Detection**.
4. Return to the app and enable **Enable automatic clicking**.
5. Test carefully before relying on it for live orders.

## Important limitations
- This does **not** use OCR or screen capture. It only works if Android Accessibility exposes the button text and clickable node.
- If the target order card is a non-accessible overlay, this starter will not detect it. A separate OCR/screen-capture implementation would be needed and would require explicit Android permission.
- The project does not bypass app security or guarantee compatibility with any particular delivery app.
- This is a debug APK build, not a Play Store release. Review the target app's terms and local rules before automating order acceptance.
