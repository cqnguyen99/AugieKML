# AugieKML

<hr/>

# Folder Structure

# Getting Started

## How to clone this repo
- Using the terminal, `cd` into the diretory where you want to clone this repo
- Run `git clone https://github.com/cqnguyen99/AugieKML.git`
- Open the project in your favorite text editor

## How to run the program
- < TO DO >

# Making Changes

[Work Tracking for this project through GitHub](https://github.com/tahaafzal5/AugieGeoTag/projects/1)

Please make a new branch for each change you want to make. This way we can keep the `master` branch clean and avoid conflicts.

- Checkout the master branch
    - `git checkout master`
- Pull all the changes so your local is up-to-date
    - `git pull`
- Before making any changes, make a new branch
    - `git checkout -b <your-branch-name>`
- Make your changes in the code and update this README accordingly
- Stage the changes you would like to keep
    - `git add <file-name>` or `git add .` to stage all changes
- Commit your changes with a meaningful commit message
    - `git commit -m "your message"`
- Rebase on master before pushing changes
    - While being on your branch that you made changes in, `git rebase master`
    - `git commit -m <message-about-rebasing>`
- To create a new branch on remote and push your changes
    - `git push origin <your-branch-name>`
- Go to the [GitHub page for this repo](https://github.com/tahaafzal5/AugieGeoTag) and create a Pull Request (PR) to get your changes merged in 
    - Click on the `Compare & pull request` button
    - Add a title, comments, etc. describing what the changes you made are
    - Add the team members are reviewers from the pane on the right hand side
    - Click the `Create pull request` button on the bottom
- After your changes have been reviewed and merged in
    - Checkout the master branch by `git switch master`
    - Pull the newest changes by `git pull`
    - Make more changes by creating a new branch and all as done above.

# Reviewing someone else's changes

Whenever someone creates a new PR, do the following before accepting their changes:

- Fetch the remote branches
    - `git fetch`
- Checkout the branch the PR is for
    - `git checkout <their-branch-name>`
- Review
    - Review all the files that changed 
    - Make sure the code makes sense
    - Make sure the code does what it claims to do
    - Make sure there are no typos, unnecessary code, misleading variable names or comments, etc
    - Test out the code with normal cases, abnormal cases, and edge cases
    - Use GitHub to make comments and give feedback if you have any
        - Wait until changes are made by the developer and review them again using the steps above 
- When satisfied, approve the PR and merge in the branch
