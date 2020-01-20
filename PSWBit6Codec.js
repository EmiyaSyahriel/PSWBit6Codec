const PSWBit6Codec = {
	encode : function(text){
		var retval = "";
		var paddedText = text.padEnd(Math.ceil(text.length / 3) * 3, ' ');
		var maxIndex = Math.floor((paddedText.length - 1) / 3);
		for(var i = 0; i <= maxIndex; i++){
			//console.log(i, i*3, maxIndex, paddedText.substr(i * 3,  3));
			retval += PSWBit6Codec.encodeIndividual3Byte(paddedText.substr(i * 3, 3));
		}
		return retval;
	},
	decode : function(text){
		var retval = "";
		if (text.length % 4 != 0) throw new Error("Given BIT6 Format is invalid, "+text.Length+" is not mod of 4");
		var maxIndex = Math.floor((text.length - 1) / 4);
		for(var i = 0; i <= maxIndex; i++){
			var index = i *4;
			retval += PSWBit6Codec.decodeIndividual4Byte(text.substr(index, 4));
		}
		return retval;
	},
	isBinary : function(txt){
		if(txt != null && txt.length % 8 == 0){
			for(i = 0 ; i < txt.length; i++){
				if(txt[i] != 0 && txt[i] != 1) return false;
			}
			return true;
		}
		return false;
	},
	binaryToString : function(binary){
		var retval = "";
		
		if(!PSWBit6Codec.isBinary(binary)) throw new Error(binary + " is not a binary string!");
		
		var chars = [];
		var i = 0;
		while(i < binary.length){
			var str = binary.substr(i, 8);
			var nb = parseInt(str, 2);
			chars.push(String.fromCharCode(nb));
			i+=8;
		}
		retval = chars.join("");
		return retval;
	},
	toByteArray : function(text){
		var retval = []
		for(i = 0; i < text.length; i++) retval.push(text.charCodeAt(i))
		return retval
	},
	createBitPad : function(text, start){return "01" + text.substr(start, 6);},
	encodeIndividual3Byte : function(text){
		if(text.length != 3) throw new Error(text+" is not an individual 3 byte!");
		var byteArray = PSWBit6Codec.toByteArray(text)
		var unEncodedBitData = "";
		var bitData = "";
		byteArray.forEach((it) =>{ unEncodedBitData += it.toString(2).padStart(8, '0'); });
		for(var i = 0; i <= 3; i++){ bitData += PSWBit6Codec.createBitPad(unEncodedBitData, i *6); }
		return PSWBit6Codec.binaryToString(bitData);
	},
	decodeIndividual4Byte : function(text){
		if(text.length != 4) throw new Error(text+" is not a 4Byte notation!");
		var byteArray = PSWBit6Codec.toByteArray(text)
		var bitData = "";
		byteArray.forEach((it) => {
			bitData += it.toString(2).padStart(8, '0').substr(2,6);
		})
		return PSWBit6Codec.binaryToString(bitData).trimEnd();
	}
}