# Makemoji-React-Native

A React Native wrapper for the MakeMoji SDK. Currently Android only.

## Installation
Copy the folder [com/makemoji/mojilib](android/app/src/main/java/com/makemoji/mojilib) into your android/app/src/main/java folder.

In your MainApplication.java add the MakeMojiReactPackage with your sdk key.
```java
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(), new MakeMojiReactPackage(MainApplication.this,"YourKey")
      );
    }
  };
```

Add the latest version of the sdk to your app's build.gradle 

[ ![MakeMoji SDK Version](https://api.bintray.com/packages/mm/maven/com.makemoji%3Amakemoji-sdk-android/images/download.svg) ](https://bintray.com/mm/maven/com.makemoji%3Amakemoji-sdk-android/_latestVersion)
```groovy compile "com.makemoji:makemoji-sdk-android:x.x.xxx" ```

Copy the folder [MakeMojiRN](MakeMojiRN) for the js files.

## Usage
Take a look at [index.android.js](index.android.js) to see how the flow of data goes from user input in the MakeMojiTextInput,
to pressing the send button, to populating the list, rendered by MakeMojiText with via the html prop. You can instead choose to use the
'plaintext' member from the onSendPress event to set the plaintext prop of MakeMojiText.

If you want to use an input target other than MakeMojiTextInput, things are a bit trickier. A MakeMojiEditTextAndroid is required
to ensure keyboard, backspace, and copy paste compatibility. Assign the view a static unique identifier in the finderTag prop.
Then, after mount, assign MakeMojiTextInput the outsideEditText with the same finderTag value. Set outsideEditText={null} to reverse
this.

To get the {html,plaintext} value of the MakeMojiEditTextAndroid, call requestHtml(shouldClear,shouldSendAnalytics) on the view. 
The result will be returned asynchronously through the function given to the onHtmlGenerated prop.

## Customization
MakeMojiTextInput and MakeMojiTextInput are copy paste extensions of the Text and TextInput components and respond to standard styling
and custmization. Descriptions of the styling available for MakeMojiTextInput can be found next to the proptypes of
[MakeMojiTextInput.js](MakeMojiRN/MakeMojiTextInput.js). Any other styling is available on request.
