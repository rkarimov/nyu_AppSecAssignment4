To simplify my search, I simply ran `grep -r 'http:' .` against the main directory with all files which highlighted the variables where I'd need to change HTTP to HTTPs

For `Second Fragment.kt` I added HTTPs to the following lines: 
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(GsonConverterFactory.create())`
`intent.data = Uri.parse("https://appsecclass.report/api/index")`


For `ThirdFragment.kt` I added HTTPs to the following lines: 
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(`

For `CardScrollingActivity.kt`, I added HTTPs to the following lines:
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(`
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory( `
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory( `


For `ProductScrollingActivity.kt`, I added HTTPs to the following lines:
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(`
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(`
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(`

For `UseCard.kt`, I added HTTPs to the following lines:
`Glide.with(this).asBitmap().load("https://appsecclass.report/" + card?.product?.productImageLink).into(image)`
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(`

For `GetCard.kt`, I added HTTPs to the following lines:
`Glide.with(this).asBitmap().load("https://appsecclass.report/" + product?.productImageLink).into(image) // Part 3 Fix adding HTTPs`
`var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(GsonConverterFactory.create()) // Part 3 Fix adding HTTPs`

For `CardRecyclerViewAdapter.kt`, I added HTTPs to the following lines:
`Glide.with(context).asBitmap().load("https://appsecclass.report/" + card.product?.productImageLink).into(image)`

For `RecyclerViewAdapter.kt`, I added HTTPs to the following lines:
`Glide.with(context).asBitmap().load("https://appsecclass.report/" + product.productImageLink).into(image)`