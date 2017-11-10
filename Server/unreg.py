import json
import sys
def unreg (a):
	if len (a) > 1:
		json_string=a[1]
	else: json_string="###"
	print (json_string)
	return json_string
unreg(sys.argv)
