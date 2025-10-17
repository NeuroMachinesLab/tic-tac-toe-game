[![java-version](https://img.shields.io/badge/java-25-brightgreen?style=flat-square)](https://openjdk.org/)

# Pure Java Neural Network Tic Tac Toe Game

<img src="https://github.com/user-attachments/assets/78345cbb-b406-4404-a358-195a3c49afcf" width="128px" alt="tic-tac-toe-game"/>

This console program allows you to read Neural Network from the
[network-easy.txt](network-easy.txt),
[network-medium.txt](network-medium.txt) or
[network-hard.txt](network-hard.txt)
file and play the Tic Tac Toe console Game.

To play the game run:
```shell
.\mvnw.cmd clean package
java -jar .\target\tic-tac-toe-game-1.0.jar
```

The Neural Network is based on
[Perceptron [1]](https://github.com/NeuroMachinesLab/perceptron) and
trained by [Q-Learning [2]](https://github.com/NeuroMachinesLab/tic-tac-toe-q-learning) data
with the program from [repository [3]](https://github.com/NeuroMachinesLab/tic-tac-toe-network).

---
[1] [Perceptron](https://github.com/NeuroMachinesLab/perceptron)<br>
[2] [Q-Learning](https://github.com/NeuroMachinesLab/tic-tac-toe-q-learning)<br>
[3] [Neural Network](https://github.com/NeuroMachinesLab/tic-tac-toe-network)

### Console interface

Example of one game:
```shell
> java -jar .\target\tic-tac-toe-game-1.0.jar

1) Easy
2) Medium
3) Hard
Choose Difficulty  [1-3]: 3

1) X player
2) O player
Select a player [1-2]: 1

| 1 | 2 | 3 |
-------------
| 4 | 5 | 6 |
-------------
| 7 | 8 | 9 |

Move with 'X' to space [1-9]: 6

|   |   |   |
-------------
|   |   | X |
-------------
|   |   |   |

AI move: 3

|   |   | O |
-------------
|   |   | X |
-------------
|   |   |   |

Move with 'X' to space [1-9]: 4

|   |   | O |
-------------
| X |   | X |
-------------
|   |   |   |

AI move: 5

|   |   | O |
-------------
| X | O | X |
-------------
|   |   |   |

Move with 'X' to space [1-9]: 7

|   |   | O |
-------------
| X | O | X |
-------------
| X |   |   |

AI move: 1

| O |   | O |
-------------
| X | O | X |
-------------
| X |   |   |

Move with 'X' to space [1-9]: 2

| O | X | O |
-------------
| X | O | X |
-------------
| X |   |   |

AI move: 9

| O | X | O |
-------------
| X | O | X |
-------------
| X |   | O |

Good game, but you lost
```