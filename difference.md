**What are the two types of Intents?**
As referenced directly from lecture notes, the two types of intents are, Explicit and Implicit Intents. Explicit Intents: have specified a component (via setComponent(ComponentName) or setClass(Context, Class)), which provides the exact class to be run, thereby ensuring the request is communicated within the app itself only. On the other hand, Implicit Intents have not specified a component; instead, they must include enough information for the system to determine which of the available components is best to run for that intent. To simplify, implicit intent is when an application asks "hey, I need someone for this task" which can lead to arbitrary process taking advantage, whearas, Implicit intent, says "hey, I trust you and know your functionality to carry out this task" and mitigates the risk of arbitrary process taking advantage of intent.

**Which of the two types of Intents are more secure?**
Explicit intent is more secure as it allows you to specify the exact activity/ component which would handle the request and ensures that such requests are handled witin the app only which mitigates the risk of external applications potentially hijacking the process or intent

**What type of Intent is shown on lines 69 to 73 of SecondFragment.kt?**
Implicit intent is leveraged which is used to target `Intent(Intent.ACTION_VIEW)`

```
var intent = Intent(Intent.ACTION_VIEW)
intent.type = "text/giftcards_browse"
intent.data = Uri.parse("https://appsecclass.report/api/index")
intent.putExtra("User", loggedInUser);
startActivity(intent)
```

**What type of Intent is shown on lines 68 to 70 of ThirdFragment.kt?**
An Explicit Intent is used here which is used to target an internal component within the application `Intent(activity, ProductScrollingActivity::class.java)`
```
var intent = Intent(activity, ProductScrollingActivity::class.java)
intent.putExtra("User", loggedInUser);
startActivity(intent)
}
}
})
```

**Which of these two Intents is the proper way to do an Intent?**
The proper way to do an intent is as done in `ThirdFragment.kt`, specifically on line 68. In this case, the ProductScrollingPage is passed to deliver the Explicit Activity intent `Intent(activity, ProductScrollingActivity::class.java)` which is passed within the application only. 



**As the last question above hinted, one of these two Intents is not correct. Fix the incorrect Intent, then in 3 sentences or less discuss in difference.txt which file you modified and why.**
In order to mitigate the risk of Implicit intents being leveraged, I started off with researching Android permissions which is the underlining concept behind intents and came across (https://developer.android.com/guide/topics/manifest/permission-element) which suggested adding permission restrction to `AndroidManifest.xml` file. Therfore, I added these lines: 
```
      <permission
          android:name="GiftCardSite_Permissions"
          android:protectionLevel="signature" />
      <!--Part 2.2  Adding permission restrictions as to mitigate risk of application accessing resources outside of its code
        per https://developer.android.com/guide/topics/manifest/permission-element -->
```
Next, I adjusted the file `SecondFragment.kt` file to remove the Implicit Intent and given the functionality similar to `ThirdFragment.kt` file, I simply added this line instead 
`var intent = Intent(activity, ProductScrollingActivity::class.java)`