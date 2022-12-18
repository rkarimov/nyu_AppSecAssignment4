In order to comply with company requirements, I commented out the following lines from `AndroidManifest.xml` file accordingly 
Accordingly, the only intent I left was in `android:name=".MainActivity"` which is loading fonts and styling from internal application and therfore does not pose a risk of launching processes outside our application

```
            <!-- Part 2.2  <intent-filter>
              <action android:name="android.intent.action.VIEW" />

              <category android:name="android.intent.category.DEFAULT" />

              <data android:mimeType="text/giftcards_use" />
                  <data android:scheme="giftcard" />
                 <data android:host="appsecclass.report"/>
              </intent-filter> -->
```

```
             <!--  Part 2.2  <intent-filter>
              <action android:name="android.intent.action.VIEW" />

              <category android:name="android.intent.category.DEFAULT" />

              <data android:mimeType="text/giftcards_buy" />
                  <data android:scheme="giftcard" />
                  <data android:host="appsecclass.report"/>
              </intent-filter> -->
```


```
              <!--   Part 2.2  <intent-filter>
                   <action android:name="android.intent.action.VIEW" />

                   <category android:name="android.intent.category.DEFAULT" />

                   <data android:mimeType="text/giftcards_browse" />
                   <data android:scheme="giftcard" />
                   <data android:host="appsecclass.report"/>
               </intent-filter> -->
```

```
<!--  Part 2.2   <intent-filter>
                        <action android:name="android.intent.action.VIEW" />

                        <category android:name="android.intent.category.DEFAULT" />

                        <data android:mimeType="text/giftcards_list" />
                        <data android:scheme="giftcard" />
                        <data android:host="appsecclass.report"/>
                    </intent-filter> -->
```