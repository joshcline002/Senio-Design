int value = 0;

void EMGprint(int pin){
  value = analogRead(pin);
  Serial1.print(value);
  delay(5);  
}
