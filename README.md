Transform documents in RSS feeds for Java (hirss)
=====================
hirss allows to transform no rss documents/info in rss feeds


Compile
-------------------
You need Apache Maven.

From root directory open a terminal and execute:
mvn assembly:assembly
mvn jar:jar

A zip file, hirss.zip will be created in target/ folder
Extract the zip (it cointains a jar file and a lib folder/)

Run
-------------------
Open a terminal and execute:
java -jar hirss.jar

A new HTTP Server listening by default on port 1225 will start and, depending on the plugins created,
RSS XML files will be generated in $HOME/.hirss/rss folder


Plugin
-------------------
TODO

Third-Party Libraries
--------------
pf4j

https://github.com/decebals/pf4j

jsoup

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
