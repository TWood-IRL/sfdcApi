# SFDC SOAP API

A Soap API that I tried to make independant that can be plugged into a project if needed 

## Background

So initially I was working in test automation using Selenium to automate the browser, a way we initially had this was 
Use the CLI Dataloader to load spreadsheets into the test org .. run the selenium scripts .. then delete the data .. 
This was painfull ... so instead I looked at the API and utilizing this and it now seems to be great for our test automation.. 

We can quickly spin up a test script with or without the browser ..perform the test .. then perform asserts... or even assert ..loads into salesforce .. 
Which then fire triggers and assert that they are set to what we are expecting.. Not sure if its usefull for anyone but might be handy for some .. 

## Getting Started

### What things you need to install the software and how to install them

* Maven 


### Installing

So I tried to make this a plugin for any maven project so hopefully all you should need to do is add this repo as a module to your project 
Also be sure to run the install-jar.bat file under the lib folder this installs the partner api which gives us the generic api objects 

#### And away you go !





## Built With

* Java
* [Maven](https://maven.apache.org/) - Dependency Management
* [Salesforce Soap Api](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/) - Used to generate RSS Feeds


## Authors

* **Thomas Woodhouse** - *Initial work*





