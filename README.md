# Analog Horror Inspired Game Using Kilt Graphics

Team members: Daisy Chan, Moyartu Manley, Wren Kuratana

Project description: This project is an analog horror inspired point-and-click game that was made using the Kilt Graphics API.

System requirements: The program should run on any system that can run Java 17 programs. All necessary assets are included within the program files.

How to run: The main class is in HorrorGame. Just click Run and you should be ready to go!

Credit: Stack overflow (https://stackoverflow.com/questions/25186631/how-to-add-loop-timer-in-java) was referenced for the implementation of the final timed scene.

Limitations: The program is designed to run in a non-scalable window that measures at 854 x 480 px. The mouse is the game's sole input, but it is not designed to handle dragging the cursor while the left mouse button is depressed. 

Performance may be inferior during the part of the game in which there is a visible timer; this is due to canvas.animate() inhibiting performance. (Probably.)

Societal impact: A notable sacrifice in this program's development was made in accessibility. The vast majority of the game is in black-and-white, at a low resolution. Label implementation is exceedingly limited. As a result, this game is not accessible to anyone with impaired vision. 

Any device that can input a left mouse button event should be compatible with our program, regardless of its form.

This program includes timed events that may cause stress, discomfort, or be entirely unfeasable to complete if the player is unable to control inputs at their prefered speed.

Anxiety and/or fear are intended consequences of our game. If either of those are unhealthy for a player to experience, we advise that you do not play our game.