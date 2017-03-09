# Whatsapp-API

An Android Library with demo application, to send media and text messages via Whatsapp on rooted device

## Installation

Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```
    dependencies {
    	compile 'com.github.omegaes:Whatsapp-API:1.0.0'
    }
````


## Usage

Check if device has whatsapp version

```
WhatsappApi.getInstance().isWhatsappInstalled() -> boolean
```

Check if device has root privilege

```
WhatsappApi.getInstance().isRootAvailable() -> boolean
```

Get list of whatsapp contacts, from whatsapp database

```
WhatsappApi.getInstance().getContacts(Context, GetContactsListener) -> void
```

Send message to one contact or list of contacts, create WMessage and WContact objects !
```
WhatsappApi.getInstance().sendMessage(List<WContact>, WMessage, Context, SendMessageListener)  -> void
```


## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

Not yet

## Author

* **Abdulrahman Babil** - *Software engineer* - [Mega4Tech](http://mega4tech.com)

## License

This project is licensed under the GNU LESSER GENERAL PUBLIC LICENSE Version 3 - see the [LICENSE](LICENSE) file for details
