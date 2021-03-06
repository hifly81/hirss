Transform documents in RSS feeds for Java (hirss)
=====================
hirss allows to transform no RSS documents/info in rss feeds


Compile
-------------------
You need Apache Maven.

From root directory open a terminal and execute:

mvn assembly:assembly

mvn jar:jar

A zip file, hirss.zip will be created in target/ folder.

Extract the zip (it cointains a jar file and a lib folder/)

Run
-------------------
Open a terminal and execute:

java -jar hirss.jar

A new HTTP Server listening by default on port 1225 will start and, depending on the plugins created,
RSS XML files will be generated in $HOME/.hirss/rss folder

Just add the name of the rss feed (file) generated inside $HOME/.hirss/rss folder in your rss feed reader;
example http://127.0.0.1:1225/b10a8db164e0754105b7a99be72e3fe5


Configuration
-------------------
Configuration is defined in hirss.properties; you can override these parameters:

\#binding address of HTTP Server

binding_address=127.0.0.1

\#binding port of HTTP Server

port=1225

\#max concurrent HTTP request

max_threads=50

\#default folder where RSS XML are generated

rss_folder=.hirss/rss

\#interval in milliseconds to upload the RSS

rss_update_period=5000

\#timeout in seconds to establish a connection to the source

rss_connect_timeout=25000



Plugin
-------------------
You can create plugins simply adding a java class annotated with @Extension and implementing the interface
SimpleRssDoc.

@Extension

public class HelloWorld implements SimpleRssDoc {

Your class must fetch data from somewhere (web scraping as an example) and returning an instance of java object
org.hifly.hirss.model.Rss

After creating your plugin simply recompile the entire project and run it.

The plugin will be automatically discovered and a new RSS XML file will be generated with your feeds.


Third-Party Libraries
--------------
pf4j

https://github.com/decebals/pf4j

jsoup

(a useful library to grab content from web pages;

it's not need by the core project, HelloWorld plugin just use it as an example)

http://jsoup.org

ROME

http://rometools.github.io/rome

jetty

http://eclipse.org/jetty

apache commons

http://commons.apache.org

apache commons codec

http://commons.apache.org/proper/commons-codec

slf4j

http://www.slf4j.org


License
--------------
Copyright 2015 Hifly81
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
the License. You may obtain a copy of the License in the LICENSE file, or at:
 
http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
