/**
 * common utils for simple extends
 * plugins should not appear here, better in separate independent file
 */

if(! String.prototype.capitalizeFirstLetter) {
	String.prototype.capitalizeFirstLetter = function() {
	    return this.charAt(0).toUpperCase() + this.slice(1);
	}
}

if (!String.prototype.format) {
	  String.prototype.format = function() {
	    var args = arguments;
	    return this.replace(/{(\d+)}/g, function(match, number) { 
	      return typeof args[number] != 'undefined' ? args[number] : match;
	    });
	  };
}