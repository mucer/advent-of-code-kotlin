{ pkgs ? import <nixpkgs> {} }:
pkgs.mkShell {
    buildInputs = with pkgs; [
        jetbrains.idea-community
        git
        jdk
    ];
}
