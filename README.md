# PSWBit6Codec
A simple base64-like string encoding method

## How to use:
### C# _(.NET & Mono Compatible)_
Put the script inside your project folder and then you can call the static method :
```cs
  PSWBit6Codec.Encode("PSWBit6Codec"); // result : "TEMWPfetMdMoYFUc"
  PSWBit6Codec.Decode("QVui^VES^VEh\fee[B@`"); // result : "EmiyaSyahriel"
```

### Kotlin _(JVM, not tested on other)_
Add your package name to the script and then you can call the function in the object :
```kotlin
  PSWBit6Codec.encode("PSWBit6Codec"); // result : "TEMWPfetMdMoYFUc"
  PSWBit6Codec.decode("QVui^VES^VEh\fee[B@`"); // result : "EmiyaSyahriel"
```

### JavaScript _(Browser Compatible)_
Include the script into your page and call the function in the object:
```html
<SCRIPT src="PSWBit6Codec.js"></SCRIPT>
```
```js
  PSWBit6Codec.encode("PSWBit6Codec"); // result : "TEMWPfetMdMoYFUc"
  PSWBit6Codec.decode("QVui^VES^VEh\fee[B@`"); // result : "EmiyaSyahriel"
```

## How it actually work?
-(Input)> "**ME**" 
-(Pad)> "**ME(space)**" 
-(Bit)> "**01001101 01000101 00100000**"
-(CutTo6)> "**010011 010100 010100 100000**" 
-(PadTo8)> "**01010011 01010100 01010100 01100000**"
-(ReString)> "**STT`**"

## License
No License, It's public domain
