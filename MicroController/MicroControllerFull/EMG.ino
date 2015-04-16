int value = 0;

void EMGprint(int pin){
  value = analogRead(pin);
  Serial.println(value);
  delay(5);  
}
