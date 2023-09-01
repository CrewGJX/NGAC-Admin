match (:policyClass {name: "superPolicyClass"})-[:inherit]->(pcs:policyClass)-[*]->(:o {name: $object})
unwind pcs as pc
match pc_path = (pc)-[*]->(:o {name: $object})
with [n1 in nodes(pc_path)] as middle, collect(distinct pc_path) as pc_path
match user_path=(:user {name: $user})-[*]->(:o {name: $object})
with [n2 in nodes(user_path) where n2 in middle and none(l in labels(n2) where l = 'o')] as middle2, pc_path
match ar_path = (:user {name: $user})-[ac*]->(dest)
where dest in middle2 and none(r in ac where type(r) = 'prohibition')
return collect(distinct ar_path) as ar_path, pc_path