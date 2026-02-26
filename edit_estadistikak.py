#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os

# Find and read the file
base_path = r"c:\Users\ikumendibe25\Documents\ERRONKAK\Erronka4_Urko"
java_file_path = os.path.join(base_path, "MAHAIGAINEKO_APLICAZIOA", "src", "ProduktuakLogika.java")

print(f"Looking for file: {java_file_path}")
print(f"File exists: {os.path.exists(java_file_path)}")

if os.path.exists(java_file_path):
    with open(java_file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Find the esportatuEstatistikak method
    start_marker = "public static void esportatuEstatistikak()"
    if start_marker in content:
        print("Found the method!")
        # Find the line number
        lines = content.split('\n')
        for i, line in enumerate(lines):
            if start_marker in line:
                print(f"Method starts at line {i+1}")
    else:
        print("Method not found")
else:
    print("File not found")
    # Try to list directory contents
    mahaiga_path = os.path.join(base_path, "MAHAIGAINEKO_APLICAZIOA")
    if os.path.exists(mahaiga_path):
        print(f"Directory exists: {mahaiga_path}")
        print(f"Contents: {os.listdir(mahaiga_path)}")
    else:
        print(f"Directory does not exist: {mahaiga_path}")

