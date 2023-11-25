<table border="0"  style="border:none!important;">
    <tr border="0"  style="border:none!important;">
        <td  border="0" style="border:none!important;">
            <img src="logo512.png" alt="Logo" width="256" border="0" />
        </td>
        <td  border="0" style="border:none!important;">
            <h1>HA QR Helper</h1>
            <p> This is a very small Android application that performs a simple string substitution when scanning QR Code.</p>
            <p> Instead of generating new QR Codes dedicated to your new **Home Assistant** instance, you can reuse previously generated codes with a different prefix.</p>
        </td>
    </tr>
</table>

# How to use it

1. Go to `app/build.gradle.kts` and edit the following strings:
   - `baseUrl` : your personal url prefix that already populates your QR codes
   - `tag` : the suffix that is appended after BASE_URL and before the tag ID
   - `haUrl`: the deeplink prefix used by Home Assistant Companion app on Android.

The templating makes this the only region in the app folder where you need to modify things.

2. Build and run in Android Studio

3. Once installed on your phone, you may need to go to the app settings and switch on the baseURL in the default definitions.

# Credits

I used **ChatGPT4** to generate most of the code, as well as drafts of the logo.

This is my solution to avoid touching at the [Home-Assistant code](https://github.com/home-assistant/android) while answering the needs described [on the Home-Assistant forum](https://community.home-assistant.io/t/is-it-absolutly-necessary-to-use-outside-url-home-assistant-io-tag-for-using-nfc-tags-qr-codes/369800/19)
