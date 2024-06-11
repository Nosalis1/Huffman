package com.example.huffman.algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private final HuffmanNode root;
    private final String encodedData;

    private HuffmanTree(HuffmanNode root, String text) {
        this.root = root;
        this.encodedData = encodeText(text, root);
//        encodeData(this.root, "", new HashMap<>());
    }

    public static HuffmanTree createHuffmanTree(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        Map<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.freq));
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            pq.add(new HuffmanNode(key, value));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(null, left.freq + right.freq, left, right);
            pq.add(parent);
        }
        return new HuffmanTree(pq.poll(), text);
    }

    public String getEncodedData() {
        return encodedData;
    }

    public HuffmanNode getRoot() {
        return root;
    }

    private void encodeData(HuffmanNode root, String code, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }
        if (root.ch != null) {
            huffmanCode.put(root.ch, code);
        }
        encodeData(root.left, code + "0", huffmanCode);
        encodeData(root.right, code + "1", huffmanCode);
    }

    private String encodeText(String text, HuffmanNode root) {
        Map<Character, String> huffmanCode = getHuffmanCode();
        StringBuilder encodedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedText.append(huffmanCode.get(ch)).append(" ");
        }
        return encodedText.toString();
    }

    public Map<Character, String> getHuffmanCode() {
        Map<Character, String> huffmanCode = new HashMap<>();
        encodeData(root, "", huffmanCode);
        return huffmanCode;
    }

    public String decodeData() {
        return decodeData(encodedData);
    }

    private String decodeData(String encodedData) {
        StringBuilder decodedData = new StringBuilder();
        HuffmanNode current = root;
        for (int i = 0; i < encodedData.length(); i++) {
            if (current.isLeaf()) {
                decodedData.append(current.ch);
                current = root;
            }
            if (encodedData.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return decodedData.toString();
    }
}
