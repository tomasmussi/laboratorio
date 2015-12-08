int CS_signal = 2;                      // Chip Select signal on pin 2 of Arduino
int CLK_signal = 4;                     // Clock signal on pin 4 of Arduino
int MOSI_signal = 5;                    // MOSI signal on pin 5 of Arduino
byte cmd_byte2 = B00010001 ;            // Command byte
int resist_value = 2;
int input;
double volt_input;

void initialize() {                     // send the command byte of value 5 (initial value)
    spi_out(CS_signal, cmd_byte2, resist_value);
}


void spi_out(int CS, byte cmd_byte, byte data_byte){                        // we need this function to send command byte and data byte to the chip
    
    digitalWrite (CS, LOW);                                                 // to start the transmission, the chip select must be low
    spi_transfer(cmd_byte);                         // sending COMMAND BYTE
    delay(2);
    spi_transfer(data_byte);                        // sending DATA BYTE
    delay(2);
    digitalWrite(CS, HIGH);                                                 // to stop the transmission, the chip select must be high
}

void spi_transfer(byte working) {
    for(int i = 1; i <= 8; i++) {                                           // Set up a loop of 8 iterations (8 bits in a byte)
     if (working > 127) { 
       digitalWrite (MOSI_signal,HIGH) ;                                    // If the MSB is a 1 then set MOSI high
     } else { 
       digitalWrite (MOSI_signal, LOW) ; }                                  // If the MSB is a 0 then set MOSI low                                           
    
    digitalWrite (CLK_signal,HIGH) ;                                        // Pulse the CLK_signal high
    working = working << 1 ;                                                // Bit-shift the working byte
    digitalWrite(CLK_signal,LOW) ;                                          // Pulse the CLK_signal low
    }
}

void setup() {
   
    pinMode(13, OUTPUT); // Configure to use the 13 pin to turn on the LED, after we will use this to turn on the device
    pinMode (CS_signal, OUTPUT);
    pinMode (CLK_signal, OUTPUT);
    pinMode (MOSI_signal, OUTPUT);
    initialize();
    Serial.begin(9600);                                                     // setting the serial speed
    Serial.println("Program Running!");
}

void loop() {


//  if (resist_value < 150){    //we use a 10k resistor, but the circuit needs an 5k, so this limits to 5k the value of the resistor
//    spi_out(CS_signal, cmd_byte2, resist_value);              //Sending the new value to the resistor
//    resist_value = resist_value + 5;
//  }

  //Code to calculate the necesary steps to reach the desired value of the resistor
    input=Serial.read();
    /*if (input<5057){
      int valor = (input-67)/34;
      spi_out(CS_signal, cmd_byte2, valor);
    }
    else */
    if (input=='1'){
      resist_value = resist_value - 1;
      spi_out(CS_signal, cmd_byte2, resist_value);
    }
    else if (input=='0'){
      resist_value = resist_value - 1;
      spi_out(CS_signal, cmd_byte2, resist_value);
    }
}



//Code for java&arduino program
//  (listen to the serial port, and send to arduino the value listenned for the resistor)
//  input=Serial.read();
//  spi_out(CS_signal, cmd_byte2, input);



//Code for switching on/off the led
//  input=Serial.read();
//  if (input =='8'){
//    digitalWrite(13, HIGH);     //if input value is 8, the led turns on
//  }
//
//  if (input =='9'){
//    digitalWrite(13, LOW);      //if input value is 9, the led turns off
//  }
//        



//Code to calculate the necesary steps to reach the desired value of the resistor
//  input=Serial.read();
//  if (input<5057){
//    int valor = (input-67)/34;
//    spi_out(CS_signal, cmd_byte2, valor);
//  }
//  else if (input=='1'){
//    resist_value = resist_value - 1;
//    spi_out(CS_signal, cmd_byte2, resist_value);
//  }
//  else if (input=='0'){
//    resist_value = resist_value - 1;
//    spi_out(CS_signal, cmd_byte2, resist_value);
//  }



//Code to calculate the resistor value, having the desired voltaje
//  volt_input=Serial.read();
//  if (volt_input>=0 && volt_input<30.1){
//    int valor = CUENTA PARA PASAR A PASOS 
//    spi_out(CS_signal, cmd_byte2, valor);
//  }
