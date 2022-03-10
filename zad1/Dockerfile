FROM ubuntu:20.04

ENV TZ=Europe/Warsaw

RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get update && apt-get install -y curl zip unzip openjdk-11-jdk
RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "$HOME/.sdkman/bin/sdkman-init.sh"
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk install kotlin && sdk install gradle
RUN curl -sSLO https://github.com/pinterest/ktlint/releases/download/0.44.0/ktlint && chmod a+x ktlint && mv ktlint /usr/local/bin/
