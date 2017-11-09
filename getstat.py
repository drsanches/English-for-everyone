import json
import sys
def getstat (a):
	if len (a) > 1:
		json_string=a[1]
	else: json_string="###"
	print (json_string)
	return json_string
getstat(sys.argv)
