import socket
# import Rpi.GPIO as GPIO  
# from time import sleep
# GPIO.setmode(GPIO.BOARD)

# GPIO.setup(11,GPIO.OUT)

HOST = '172.16.5.132'  	
PORT = 12345		# Arbitrary port number
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print 'Socket created'
try:
    s.bind((HOST, PORT))
except socket.error , msg:
    print 'Bind failed. Error code: ' + str(msg[0]) + 'Error message: ' + msg[1]
    exit()
print 'Socket bind complete'
s.listen(10)
print 'Socket now listening'
while 1:
	print 'hi'
	# Accept the connection 
	(conn, addr) = s.accept()
	print 'Connected with ' + addr[0] + ':' + str(addr[1])
	stored_data = ''
	# RECEIVE DATA
	datastream = conn.recv(1024)
	data = datastream
	print data;
	# PROCESS DATA
	tokens = data.split(' ',2)              # Split by space at most twice
	appliance= tokens[0]                    # The first token is the appliance
	state= tokens[1]
	print tokens
	   
	print 'Appliance: ' + appliance
	print 'State: ' + state

	if appliance.lower()=='lights':         
	   if (state.lower()=='on'):   # insert code to switch on lights
	      reply = 'Lights have been switched '+state                
	elif appliance.lower()=='fan':          
	   if state.lower()=='on':  	    # insert code to switch on fan.
	      reply = 'Fan has been switched '+state                      
	elif appliance.lower()=='television':   
	   if state.lower()=='on':   	# insert code to switch on fan.
	      reply= 'Television has been switched '+state
	else:
	   reply = 'Unknown command'
	
	# SEND REPLY
	conn.send(reply)
	# Close connection
        conn.close() 
