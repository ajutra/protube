# Pro tube project

## Implemented features
- [X] **Show static content** (Obtained via video grabber).
- [X] **Basic auth** (register/login/logout): You can do this on the top right of every page.
- [X] **Upload a video**: You can do this when logged in, on the profile page, `My videos` section.
- [X] **Leave comments**: When logged in, go to any video details page, you can leave a comment on `Comments` section.
- [X] **Own videos management**: When logged in, you can delete or edit your videos from profile or video details pages, there is a `More` button, represented by three vertical dots.
- [X] **Own comments management**: When logged in, you can delete or edit your comments from profile or video details pages, there is a `More` button, represented by three vertical dots.

### Improvements

- **Night mode**: By default it is set to your system mode setting, you can change it via a button on the top right corner.
- **Like/Dislike videos**: When logged in, you can like/dislike a video via video details page.
- **User acceptance ratio of videos**: A user acceptance ratio (based on like/dislike ratio) of videos, is displayed on home and video details pages.
- **User authentication on the backend**: When a user registers/logs in its checked/stored in the database through backend, also the passwords of new users are encrypted using BCrypt algorithm.
- **Double database setup**: The backend has two databases (the main one, with postgreSQL, and the search one, with MongoDB), when storing/deleting/editing data, the two databases are updated at the same time.
- **Search bar**: Using the double database setup, we implemented a search functionality, in order to search for videos queries are performed against the Mongo database, you can search a video by its title, description or user who uploaded it. This search bar is located at the top center of every page.

## Instructions to run the environments

First of all you need to meet the [tech requirements](#tech-requirement). Then you need to download a video sample using the [video grabber tool](#tooling).

Once you have done that you need to [edit the .env file](backend/.env) and replace the `ENV_PROTUBE_STORE_DIR` variable value with the absolute path to your videos folder (don't forget the slash `/` at the end of the path!).

Now you need to start the databases, run the following command from the root folder of the project:

```commandline
docker compose up -d
```

### DEV environment

* **Backend**:

From your IDE, run `ProtubeBackApplication` with `dev` profile

OR

Navigate in a terminal to `backend` folder and run the following command:

```commandline
mvn spring-boot:run -P dev
```

* **Frontend**:

Navigate in a terminal to `frontend` folder and run the following command to install dependencies:

```commandline
npm install
```
Once dependencies are installed properly run the following code to execute the application in `dev` mode:

```commandline
npm run dev
```

This command above will open the port 5173 where you can access from the browser.

### PROD environment

From your IDE, run `ProtubeBackApplication` with `prod` profile

OR

Navigate in a terminal to `backend` folder and run the following command:

```commandline
mvn spring-boot:run -P prod
```

This command above will open the port 8080 where you can access from the browser.

>[!IMPORTANT]
>To ensure a correct behavior when testing both environments, remember to delete database data when changing between them, you can achieve that by running the following commands from the root folder of the project:
>```commandline
>docker compose down
>sudo rm -rf docker-postgres
>rm -rf docker-mongodb
>```
>
>Also remember to log out and register again if you delete your database data, otherwise you will get errors when trying to perform user operations because the backend will not recognise your user.

## Tech requirement

Before starting coding you have to be sure you have the following software already installed:

### Backend

* Java 21

### Frontend

* Node 20

### Database

* Docker
  
### Video grabber

* yt-dlp 
  * On Linux / Windows with WSL
    * ```commandline
      sudo wget https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -O /usr/local/bin/yt-dlp
      // And after:
      sudo chmod a+rx /usr/local/bin/yt-dlp
      ```
    * On mac
      ```commandline
      brew install yt-dlp
      ```
* ffmpeg
    * On Windows: Download the ffmpeg executable from FFmpeg’s official website. Extract the files and add the bin
      directory to your system’s PATH.
    * On macOS: Use Homebrew to install ffmpeg:
      ```commandline
      brew install ffmpeg
      ```
    * On Linux:  On Linux / Windows with WSL Install ffmpeg using your package manager. For example, on Ubuntu:
      ```commandline
        sudo apt-get update
        sudo apt-get install ffmpeg
      ```
      
Remember to remove this packages in case you don't want after the project you can execute the following command
```commandline
sudo apt-get remove package // (yt-dlp or ffmpeg)
```
* Python 3x

## Tooling

### Video grabber

#### Installation
First step is to have configured a WSL in your Windows Machine. In case you use unix-like system (linux or mac) you can follow sim

This script here help you to generate a default content for the web.

Once you have resolved the dependencies explained above you can run the following command inside `tooling/videoGrabber`
folder.

```commandline
python3 main.py --store={Store_Folder} --id=10 --recreate --videos={Path to video_list.txt}
```

Explanation of the parameters:

| Attribute | Type                           | Description                                                                                                     | Example                                                       |
|-----------|--------------------------------|-----------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| id        | integer                        | Number to seed random                                                                                           | `--id=2`                                                      |
| store     | string (path to local machine) | Folder where videos download will be downloaded                                                                 | `--store=/usr/user/home/videos`                               |
| recreate  | boolean                        | Do overwrite of the store folder                                                                                | `--recreate` or `--recreate=False`                            |
| videos    | string (path to local machine  | Path where videos list is saved. there is a default videos inside `resources` folder. But it can be overwritten | `--videos=/src/user/home/LS-protube/resources/video_list.txt` |

The command will generate 3 files per every video disposed in `resources/video_list.txt`

* *.mp4: The video cutted
* *.webp: The thumbnail
* *.json: Metadata info about the video
