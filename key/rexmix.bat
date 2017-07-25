call G:\project\key\TouchClean\clean.bat
call G:\project\key\TouchClean\build.bat
cd \project\key\TouchClean
copy G:\project\clean_project\TouchClean\app\build\outputs\apk\app-release.apk G:\project\key\TouchClean
cd \project\key\TouchClean
ren app-release.apk TouchClean.apk
java -jar AndResGuard.jar TouchClean.apk
cd TouchClean\
zipalign -c -v 4 TouchClean_signed_aligned.apk
set /p apkname= >nul
ren TouchClean_signed_aligned.apk  TouchClean_%apkname%_signed_aligned.apk
cd ..
cd ..