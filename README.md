<h2 align="center">Floaty</h2> </br>

<p align="center">
:rocket: Floaty is a customizable floating widget for your Android apps that allows you to add a floating widget to your app's interface.
</p> </br>

<p align="center">
<img src="https://github.com/Breens-Mbaka/Searchable-Dropdown-Menu-Jetpack-Compose/assets/72180010/6b9812ab-1381-4943-bd77-6f91756a4731" width="280"/>
</p>

## How to include it into your project

### Step 1: Add the JitPack repository to your project build.gradle file:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the Floaty dependency to your app's build.gradle file:

```gradle
dependencies {
    implementation("com.github.Breens-Mbaka:Floaty:1.0.1")
}
```

### Step 3: Configure the floating widget by providing the custom layout resource ID in your Kotlin code:

``` Kotlin
   CustomLayoutConfiguration.customLayoutResourceId = R.layout.floating_widget
```

### Step 4. Add the "SYSTEM_ALERT_WINDOW" permission to your AndroidManifest.xml file:

``` xml
   <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
```

### Step 5: Register the FloatyWidget service in your AndroidManifest.xml file:

``` xml
   <service
            android:name=".FloatyWidget"
            android:enabled="true"
            android:exported="false" />
```

### Step 6. Show floating widget but make sure the overlay permission is granted or else request user to give permission

``` Kotlin
   val showFloatingWidgetButton = findViewById<Button>(R.id.showFloatingWidgetButton)
        showFloatingWidgetButton.setOnClickListener {
            if (checkOverlayDisplayPermission(context = this)) {
                showFloatingWidget(context = this)
            } else {
                requestOverlayDisplayPermission(context = this, activity = this)
            }
        }
```

### Step 7: If permission to overlay other apps is granted, start the service that will show the floating widget:

``` Kotlin
   private fun showFloatingWidget(context: MainActivity) {
        context.startService(
            Intent(
                context,
                FloatyWidget::class.java,
            ),
        )
    }
```

### Step 8. Close the floating widget on click actions

``` Kotlin
   // In your Activity/Fragment send an intent to a broadcast receiver that will close the widget
   val closeWidgetButton = findViewById<Button>(R.id.closeWidgetButton)
        closeWidgetButton.setOnClickListener {
            val closeFloatingWidgetIntent = Intent(ACTION)
            sendBroadcast(closeFloatingWidgetIntent)
        }
```

### Step 9. Close the floating widget when the application is fully closed

``` Kotlin
   // In your Activity/Fragment send an intent to a broadcast receiver that will close the widget
   override fun onDestroy() {
        super.onDestroy()
        val closeFloatingWidgetIntent = Intent(ACTION)
        sendBroadcast(closeFloatingWidgetIntent)
    }
```

### Other configurations you can make to the floating widget

``` Kotlin
    CustomLayoutConfiguration.floatingWidgetWidth = 0.45f //Configure width of the floating widget
    CustomLayoutConfiguration.floatyWidgetHeight = 0.30f //Configure height of the floating widget
    CustomLayoutConfiguration.floatingWidgetPosition = Gravity.TOP //Configure the psosition of the floating widget on the screen
```

### Reporting Issues and Requesting Features✨
If you encounter any issues or have feature requests, please create a new issue in this repository.

### Supporting Floaty :heart:
Support it by joining __[stargazers](https://github.com/Breens-Mbaka/Floaty/stargazers)__ for this repository. :star: <br>
Also __[follow](https://github.com/Breens-Mbaka)__ me for my next creations! 🤩

### License

```
Copyright 2023 Breens-Mbaka

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
