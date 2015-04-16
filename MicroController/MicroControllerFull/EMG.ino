int value = 0;

void EMGprint(int pin){
  value = analogRead(pin);
  Serial.print(value);
  delay(5);  
}
