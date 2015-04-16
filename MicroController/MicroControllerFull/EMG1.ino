int value = 0;

void EMGprint(){
  value = analogRead(A0);
  Serial.print("EMG1 : ");
  Serial.print(value);
  delay(5);  
}
