import onnx
model = onnx.load("model.onnx")

output_names = [node.name for node in model.graph.output]
print(output_names)
