# PostDownloader
PostDownloader is lightweight Scala application created to download posts 
JSON from https://jsonplaceholder.typicode.com/ API and save them to files.

This app was created using:<br> 
<b>Scala</b>: 2.13.1<br> 
<b>SBT</b>: 1.3.8

## Installation
1. Install Scala and SBT
2. Clone the repository
3. From root directory run "sbt run" command.

## Configuration
By default, posts will be saved in "src/main/resources/posts". 
If you want to choose another directory to save them - change 
```application.posts.downloadPostsToDirectory``` to your directory path.
