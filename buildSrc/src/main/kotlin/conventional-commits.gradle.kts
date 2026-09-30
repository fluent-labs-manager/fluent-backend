package buildsrc.convention

plugins {
    id("it.nicolasfarabegoli.conventional-commits")
}

conventionalCommits {
    warningIfNoGitRoot = true
    types = listOf("build", "chore", "docs", "feat", "fix", "refactor", "revert", "style", "test", "ci")
    scopes = emptyList()
    successMessage = "Сообщение коммита соответствует стандартам Conventional Commit."
    failureMessage = "Сообщение коммита не соответствует стандартам Conventional Commit."
}
