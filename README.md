wishlist_hackugyo
=================

wishlist (orinally from https://github.com/kevinsawicki/wishlist )

Import
--------------------

```
git clone https://github.com/hackugyo/wishlist_hackugyo.git PROJECT/PATH/wishlist
```

Edit PROJECT/PATH/app/build.gradle :


``` groovy:PROJECT/PATH/app/build.gradle

…
dependencies {
    compile 'com.android.support:appcompat-v7:19.1.+'
    …
    compile project(':wishlist')
    …
}
…

```

Edit PROJECT/PATH/settings.gradle : 

```
include ':app', ':wishlist'
```
