# Progress Report

Mark Beussink & Cameron Niccum

We are mostly on track.  We have most of the project left to implement but we have made almost all of the decisions.  Our *EBNF* could use some work, but it's solid for now.  As far as the division of labor is concerned, the first lever/parser we wrote was done via *pair programming*, which is the plan for this project.  In the todo list bellow, suggestions are made for who is responsible for considereing or knowing how to do what, but we intend to do it in Cameron's kitchen like we did for the assignment.

[GitHub repo](https://github.com/MRBeussink/JotDown) 



## To-do

### Drawing Board Ideas

- [ ] Decide about tables.  What should the syntax be?
- [x] Write EBNF

### Programming Tasks

- [ ] Research Java I/O
      - [x] Implement a test
      - [ ] Check how to take a file name from terminal @cmniccum

- [ ] Research *Finite State Machines* for parsing
      - [ ] Implement a test
      - [ ] Make an interface @mrbeussink

- [ ] Research `StringBuilder`

      We can use this to store the output instead of concating to a `String` or gradually writing to a file.  Then, at the end, we'd write that to the output stream.

- [ ] Write interfaces for most of it @mrbeussink

- [ ] Make header for HTML pages @cmniccum

- [ ] Write `Lexer` @cmniccum

- [ ] Write `Parser` @mrbeussink

- [ ] Finish `LexerToken` @mrbeussink

      - [x] Write cases for tokens
      - [ ] Look into adding behaviors for the *FSM*

- [ ] Write `Driver`

###  Paperwork Tasks

- [x] Write Proposal
- [x] Write Progress Report
- [ ] Submit Project to D2L