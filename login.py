import json
import sys
def login (a):
	if len (a) > 1:
		json_string=a[1]
	else: json_string="###"
	print (json_string)
	return json_string
login(sys.argv)
