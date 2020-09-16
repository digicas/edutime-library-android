# EduTime Library

Android packaged library for communication between educational apps and EduKids launcher environment

Requirements on information exchange is in docs [information-exhange](docs/information-exchange.md)

---

## API Reference

You can always refer to code packaged with provided .poms, however you can refer to somewhat up-to-date
[API](docs/sdk-api.md). Further changes will be documented in-code and the aforementioned API will
not be updated any more.

---

## Usage

Always up-to-date sample usage would be in package [app](app/). Please note that this sample
contains exclusively Kotlin code, as Java is not feasible to maintain anymore at this time.

In case your main focus is not _only_ Android, refer to additional SDK implementation
[outside](https://github.com/digicas/) this repository. All main platforms should be covered though.
If not make sure to submit an issue as a request.

---

## Installation

Generally there are two ways to get the library to your project. Local and Remote methods.
**Remote** method is strongly suggested as stable versions will be available through this channel.

### Remote

**Recommended approach**. This ensures you're only using tested builds marked as stable by the
developer.

1) To your **project-level** `build.gradle` add following:

```groovy
subprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2) To your **module-level** `build.gradle` add following:

```groovy
dependencies {
    def edu_version = "" // always check jitpack badge
    implementation "cz.edukids:sdk:$edu_version"
}
```

3) Sync your project and you're good to go

### Local

_This approach is not recommended and thus not supported._ You're either ways allowed to try out
the bleeding edge builds to be ready when the new changes come out, or verify bug fixes when
prompted to by the developers.

1) Clone this repository

2) Run following command in the folder you've just downloaded your repository to:

* Unix
    * `./gradlew assemble publishToMavenLocal`
* Windows
    * `gradlew assemble publishToMavenLocal`

Please make sure you have a JAVA_HOME system environment variable set up, otherwise you will
encounter issues. Any support requests for this method are disregarded, as bleeding edge builds
cannot be tracked.

This command builds and publishes this library's fragment to your local cached maven repository
(~/.m2).

3) To your **module-level** `build.gradle` add following:

```groovy
dependencies {
    def edu_version = "" // see below *
    implementation "cz.edukids:sdk:$edu_version"
}
```

\* Version can be found in the [Publishing](buildSrc/src/main/java/Publishing.kt) file.

4) To your **project-level** `build.gradle` add following:

```groovy
subprojects {
    repositories {
        mavenLocal()
    }
}
```

5) Sync your project and you're good to go

---

## Versioning

Versioning uses [Semver](https://semver.org/) but in somewhat Google-fied way. Versions are
appended with either of 4 suffixes:

* `devXX` (ie `dev01`)
    * API can change significantly
    * API can be removed
    * API can be added
    * API can be temporarily added for testing purposes
* `alphaXX` (ie `alpha01`)
    * API can change significantly
    * API can be removed
    * API can be added
* `betaXX` (ie `beta01`)
    * API cannot change _unless circumstances require the change_
* `rcXX` (ie `rc01`)
    * Version will be soon ready for LTS release

Version that does **not** contain the suffix is considered LTS release. You're strongly compelled
to use wildcards for the "fix" place of the given version. (ie `1.2.*`) Bugfixes will be applied
with your new builds without any additional effort from you whatsoever.
