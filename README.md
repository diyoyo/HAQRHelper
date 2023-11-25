# HAQRHelper

This is a very small Android application that performs a simple string substitution when scanning QR Code.

Instead of generating new QR Codes dedicated to your new **Home Assistant** instance, you can reuse previously generated codes with a different prefix.

# How to use it

Go to `app/build.gradle.kts` and edit the following strings:
- `baseUrl` : your personal url prefix that already populates your QR codes
- `tag` : the suffix that is appended after BASE_URL and before the tag ID
- `haUrl`: the deeplink prefix used by Home Assistant Companion app on Android.


The templating makes this the only region in the app folder where you need to modify things.

# Credits

I used **ChatGPT4** to generate most of the code, as well as drafts of the logo.
