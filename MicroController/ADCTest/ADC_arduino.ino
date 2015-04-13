/** This is an example of an Arduino source file **/

void setup()
{
  Serial.begin(9600);  //setup STDOUT serial com, baud rate = 9600
}

void main()
{
  int adc_val = 0;
  adc_val = analogRead(0);
  Serial.println(adc_val);
}
  