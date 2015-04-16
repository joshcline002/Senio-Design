void setup()
{
  SPIsetup();
  Serial.begin(9600);
}

void loop()
{
  EMGprint();
  SPIprint(); 
}
