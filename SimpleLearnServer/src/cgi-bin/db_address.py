def get_db_address():
    file = open('dbaddress.txt', 'r')
    filename = file.read()
    file.close()
    return filename