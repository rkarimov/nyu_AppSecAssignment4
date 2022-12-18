I started this part by closely analyzing the different calls made `CardInterface.kt` file to understand which can be exploited more readily. 
This centered my focus on the @PUT call which talks directly with `UseCard.kt` file via the call `"/api/use/{card_number}"` which passes the card number. I immediately thought that one can potentially tamper with this PUT request as I didn't see any validation of user being done in `UseCard.kt` file. Specifically, the authorization token does not check user permissions nor who it belongs to within the `UseCard.kt` file. Thus, a user can exploit this vulnerablitiy through using their own token and continously send out `@PUT` requests to brute force different iterations of card numbers until they have a correct via a valid response code. `UseCard.kt` only combines the token with loggeedInUser:

```
var token : String = "Token " + loggedInUser?.token.toString() // Part 4 - Vuln: we are simply combining token and logged in user w/o permission check
Log.d("Token check", token) // Part 4 - token presence is checked, but its not checked whether it actually belongs owner of card. Major flaw
```
As its not checked that the authorization token belongs to card user, this flaw is easily exploited via the iteration or brute force function described above. 

When looking at a fix for this problem, I realized that this was akin to the issue we had in assignment 2 for CSRF. Thus, I thought that a more adaquate permissions check in the header to ensure that the user in session is indeed the card holder should be adaquate to mitigate this flaw. Following the article (https://medium.com/android-news/token-authorization-with-retrofit-android-oauth-2-0-747995c79720), I recommend adding `@Header("Authorization": Token <insert token here>)` within the  `CardInterface` class and seperate `@PUT` as to ensure token is validated when the card number is passed as w/o this, the card number is passed w/o any token which allows the brute force attack mentioned above. 

something similar to this would ensure token is adaquately sent to `UseCard.kt` for authentication as referenced in article above. 
```
@PUT("/api/use/{card_number}")
fun useCard(@Header("Authorization") token: String): Call<PostsResponse>
``
The above function would ensure the session user (logged in) has a matching token to one assigned to card and called in `UseCard.kt` file 