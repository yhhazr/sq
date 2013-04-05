if(!String.prototype.trim) {
	String.prototype.trim = function () {
		return this.replace(/^\s+|\s+$/g,'');
	}
}

if(!String.prototype.getStringLength) {
	String.prototype.getStringLength = function () {
		var length = 0;
		for (var i = 0; i < this.length; i++) {
			if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0) {
				length += 2;
			} else {
				length++;
			}
		}
		return length;
	}
}

// 判断某个字符是否为汉字
if (!String.prototype.isCHS) {
	String.prototype.isCHS = function (i) {
		if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0) {
			return true;
		} else {
			return false;
		}
	}
}

if (!String.prototype.strToChars) {
	String.prototype.strToChars = function(){
		var chars = new Array();
		for (var i = 0; i < this.length; i++){
			chars[i] = [this.substr(i, 1), this.isCHS(i)];
		}
		String.prototype.charsArray = chars;
		return chars;
	}
}

// 截取字符串
if (!String.prototype.subCHString) {
	String.prototype.subCHString = function (start, end) {
		var length = 0,
		str = "";
		this.strToChars();	
	for	(var i = 0; i < this.length; i++) {
		if (this.charsArray[i][1]) {
			length += 2;
		} else {
			length ++;
		}

		if (end < length) {
			return str;
		} else if (start < length) {
			str += this.charsArray[i][0];
		}
	}
	return str;
	}
}